const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const {CleanWebpackPlugin} = require('clean-webpack-plugin')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const MiniCssExtractWebpackPlugin = require('mini-css-extract-plugin')
const OptimizeCssExtractPlugin = require('optimize-css-assets-webpack-plugin')
const TerserWebpackPlugin = require('terser-webpack-plugin')
const {BundleAnalyzerPlugin} = require('webpack-bundle-analyzer');

const isDev = process.env.NODE_ENV === 'development'
const isProd = process.env.NODE_ENV === 'production'

const optimization = () => {
    const config = {
        splitChunks: {
            chunks: 'all'
        }
    }

    if (isProd) {
        config.minimizer = [
            new OptimizeCssExtractPlugin(),
            new TerserWebpackPlugin()
        ]
    }

    return config
}

const filename = extention => isDev ? `[name].${extention}` : `[name].[contenthash].${extention}`

const cssLoaders = extra => {
    const loaders = [
        {
            loader: MiniCssExtractWebpackPlugin.loader,
            options: {
                hmr: isDev,
                reloadAll: true
            }
        },
        'css-loader'
    ]

    if (extra) {
        loaders.push(extra)
    }

    return loaders
}

const babelLoader = (presets, plugins) => {
    const loader = {
        loader: 'babel-loader',
        options: {
            presets: [
                '@babel/preset-env'
            ]
        }
    }

    if (presets) {
        loader.options.presets = [...loader.options.presets, ...presets]
    }

    if (plugins) {
        loader.options.plugins = plugins
    }

    return loader
}

const jsLoaders = () => {
    const loaders = [
        babelLoader([], [
            '@babel/plugin-syntax-class-properties',
            '@babel/plugin-proposal-class-properties'
        ])
    ]

    if (isDev) {
        loaders.push('eslint-loader')
    }

    return loaders
}

const plugins = () => {
    const basePlugins = [
        new HtmlWebpackPlugin({
            template: './index.html',
            minify: isProd
        }),
        new CleanWebpackPlugin(),
        new CopyWebpackPlugin({
            patterns: [
                {
                    from: path.resolve(__dirname, 'src/favicon.ico'),
                    to: path.resolve(__dirname, 'dist')
                }
            ]
        }),
        new MiniCssExtractWebpackPlugin({
            filename: filename('.css'),
        })
    ]

    if (isProd) {
        basePlugins.push(new BundleAnalyzerPlugin())
    }

    return basePlugins
}

module.exports = {
    context: path.resolve(__dirname, 'src'),
    mode: 'development',
    entry: {
        main: [
            '@babel/polyfill',
            './index.jsx',
        ],
        analytics: './analytics.ts'
    },
    output: {
        filename: filename('.js'),
        path: path.resolve(__dirname, 'dist'),
        publicPath: ''
    },
    resolve: {
        extensions: [
            '.js'
        ],
        alias: {
            '@': path.resolve(__dirname, 'src'),
            '@model': path.resolve(__dirname, 'src/model')
        }
    },
    optimization: optimization(),
    devServer: {
        port: 4200,
        hot: isDev
    },
    devtool: isDev ? 'source-map' : false,
    plugins: plugins(),
    module: {
        rules: [
            {
                test: /\.css$/,
                use: cssLoaders()
            },
            {
                test: /\.less$/,
                use: cssLoaders('less-loader')
            },
            {
                test: /\.s[ac]ss$/,
                use: cssLoaders('sass-loader')
            },
            {
                test: /\.(png|jpg|jpeg|gif|bmp|svg)/,
                use: ['file-loader']
            },
            {
                test: /\.(ttf|woff|woff2|eot)/,
                use: ['file-loader']
            },
            {
                test: /\.xml/,
                use: ['xml-loader']
            },
            {
                test: /\.csv/,
                use: ['csv-loader']
            },
            {
                test: /\.m?js$/,
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: jsLoaders()
            },
            {
                test: /\.ts$/,
                exclude: /node_modules/,
                use: babelLoader([
                    '@babel/preset-typescript'
                ])
            },
            {
                test: /\.jsx$/,
                exclude: /node_modules/,
                use: babelLoader([
                    '@babel/preset-react'
                ])
            }
        ]
    }
}
