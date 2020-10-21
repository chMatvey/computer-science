import { Post } from "@model/post";
import './styles/index.css'
import json from './assets/json.json'
import image from './assets/image.jpg'
import xml from './assets/xml.xml'
import csv from './assets/csv.csv'
import * as $ from 'jquery'

const post = new Post('Cape Fiolent', image)

console.log('Post:', post.toString())
console.log('Json:', json)
console.log('Xml', xml)
console.log('Csv', csv)

$('pre').html(post.toString())
