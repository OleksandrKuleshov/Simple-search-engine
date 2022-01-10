_**Findwise** code assignment_

This is a spring boot app, you should be able to just run it

I am attaching postman collection with calls with test data
as well as with a call to search by term.

Just in case I add endpoint calls here as well.

POST:127.0.0.1:8080/api/document/add-list

body

```
[
{
"id":"1",
"content":"The brown fox jumped over the brown dog."
},
{
"id":"2",
"content":"The lazy brown dog, sat in the corner"  
},
{
"id":"3",
"content":"The Red Fox bit the lazy dog!"
}
]
```

GET: 127.0.0.1:8080/api/document/find?term=brown