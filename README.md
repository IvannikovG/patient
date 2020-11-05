## Test task Health Samurai. Georgii Ivannikov
![alt text](https://github.com/[username]/IvannikovG/patient/master/HS-CANDIDATE-GOSHA.png?raw=true)
### Projects consists of api, that hosts jsons, and front-end made with reagent / re-frame

##### To make the website work one should use 2 commands:
 `clj -X app.router/main` from *your-path*/patient/patient-api
 `clj -A:fig -b app --repl ` from *your-path*/patient/patient-frontend
#####  It assumes you to have postgres running on 5432 port and your db is called clj1database
##### For now this is hardcoded. You might also need to create table called patients with fields
###### [*id* | *fullname* | *gender* | *birthdate* | *address* | *insurance*]

##### Workflow:
###### 08.10 - 15.10: Check out to use clojure
###### 15.10 - 22.10: Learn clojure instruments and tools setup
###### 22.10 - 28.10: Setup backend
###### 28.10 - ongoing: Setup frontend

###### Current TODOs: 
###### ⋅⋅⋅Put hardcoded stuff into .env
###### ⋅⋅⋅Routing on frontend
###### ⋅⋅⋅Add css (currently app looks not really well)
###### ⋅⋅⋅More tests...
