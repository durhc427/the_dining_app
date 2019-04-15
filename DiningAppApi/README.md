# The Dining App API

base url: https://the-dining-app-api.appspot.com

**Note:** For convenience, we have made the following mappings:
everybody -> Everybody's Kitchen
village -> USC Village Dining Hall
parkside -> Parkside Restaurant & Grill

## All Dining Halls
Mapping: `/all`
Request Method: `GET`
Return Type: `JSONArray`
Desciption: Returns all dining hall data for the day
Parameters: none
Example: `https://the-dining-app-api.appspot.com/all`


## One Dining Hall
Mapping: `/dininghall`
Request Method: `GET`
Return Type: `JSONObject`
Desciption: Returns dining hall data for current day
Parameters: `dininghall` : dining hall name
Example: `https://the-dining-app-api.appspot.com/dininghall?dininghall=village`



## Dining hall at date
Mapping: `/pulldate`
Request Method: `GET`
Return Type: `JSONObject`
Desciption: Returns dining hall data for specific date
Parameters:
	`name` : dining hall name
	`year`: query year
	`month`: query month
	`day`: query day
Example: `https://the-dining-app-api.appspot.com/pulldate?name=parkside&year=2019&month=03&day=13`


## Pulling all dining halls on day
Mapping: `/alldhdate`
Request Method: `GET`
Return Type: `JSONArray`
Desciption: Returns all dining hall data for specific date
Parameters:
	`year`: query year
	`month`: query month
	`day`: query day
Example: `https://the-dining-app-api.appspot.com/alldhdate?year=2019&month=03&day=13`


## User login
Mapping: `/login`
Request Method: `POST`
Return Type: `JSONObject`
Desciption: whether login was successful or not
Parameters:
	`name`: username
	`password`: password
Example: `https://the-dining-app-api.appspot.com/login?name=JMiller&password=tomcat`


## Register User
Mapping: `/register`
Request Method: `POST`
Return Type: `JSONObject`
Desciption: whether login was successful or not
Parameters:
	`name`: username
	`password`: password
Example: `https://the-dining-app-api.appspot.com/register?name=JMiller&password=tomcat`


## Add Dish
Mapping: `/adddish`
Request Method: `GET`
Return Type: `JSONObject`
Desciption: whether adding dish was successful or not
`{ "addStatus" : true }`
Parameters:
	`name`: user name
	`dish`: dish name
Example: `https://the-dining-app-api.appspot.com/adddish?username=JMiller&dish=Sausages`

## Get Favorites
Mapping: `/getfavs`
Request Method: `GET`
Return Type: `JSONObject`
Desciption: returns JSONObject with JSONArray containing user favorite dishes
Parameters:
	`name`: user's query method
Example: `https://the-dining-app-api.appspot.com/getfavs?username=JMiller`
```json
{
    "dishes": [
        "Sesame Soy Green Tea Soba Noodles",
        "Bean Sprouts",
        "Basil Pesto"
    ]
}
```

## Get Allergens
Mapping: `/getallergens`
Request Method: `GET`
Return Type: `JSONObject`
Desciption: returns JSONObject with JSONArray containing user allergens
Parameters:
	`name`: user's query method
Example: `https://the-dining-app-api.appspot.com/getallergens?username=JMiller`
```json
{
    "alergens": [
        "Peanuts"
    ]
}
```