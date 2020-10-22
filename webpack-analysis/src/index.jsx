import { Post } from "@model/post";
import './styles/index.css'
import json from './assets/json.json'
import image from './assets/image.jpg'
import xml from './assets/xml.xml'
import csv from './assets/csv.csv'
import * as $ from 'jquery'
import './styles/less.less'
import './styles/scss.scss'
import './babel.js'
import React from 'react'
import { render } from 'react-dom'

const post = new Post('Cape Fiolent', image)

console.log('Post:', post.toString())
console.log('Json:', json)
console.log('Xml:', xml)
console.log('Csv:', csv)

$('pre').html(post.toString())

const App = () => (
    <div className="container">
        <h1>Webpack</h1>

        <hr/>

        <div className="logo"></div>

        <hr/>

        <pre></pre>

        <div className="box">
            <h2>Less</h2>
        </div>

        <div className="card">
            <h2>SCSS</h2>
        </div>
    </div>
)

const app = document.getElementById('app')
render(<App/>, app)
