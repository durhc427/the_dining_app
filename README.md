# The Dining App API

base url: https://thediningapp.appspot.com


**Note:** no default page setup for url yet!

**Note:** For convenience, we have made the following mappings:
- everybody -> Everybody's Kitchen
- village -> USC Village Dining Hall
- parkside -> Parkside Restaurant & Grill

## All Dining Halls
- Mapping: `/all`
- Request Method: `GET`
- Return Type: `JSONArray`
- Desciption: Returns all dining hall data for the day
- Parameters: none
- Example: `https://thediningapp.appspot.com/all`


## One Dining Hall
- Mapping: `/dininghall`
- Request Method: `GET`
- Return Type: `JSONObject`
- Desciption: Returns dining hall data for current day
- Parameters: `dininghall` : dining hall name
- Example: `https://thediningapp.appspot.com/dininghall?dininghall=village`



## Dining hall at date
- Mapping: `/pulldate`
- Request Method: `GET`
- Return Type: `JSONObject`
- Desciption: Returns dining hall data for specific date
- Parameters:
	- `name` : dining hall name
	- `year`: query year
	- `month`: query month
	- `day`: query day
- Example: `https://thediningapp.appspot.com/pulldate?name=parkside&year=2019&month=03&day=13`


## Pulling all dining halls on day
- Mapping: `/alldhdate`
- Request Method: `GET`
- Return Type: `JSONArray`
- Desciption: Returns all dining hall data for specific date
- Parameters:
	- `year`: query year
	- `month`: query month
	- `day`: query day
- Example: `https://thediningapp.appspot.com/alldhdate?year=2019&month=03&day=13`
