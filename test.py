# Testing API
from main_test import *
ms = MenuScraper()
ms.parse_menus()
# print(ms.return_menu_json())
# print(ms.menus.toJSON())
mylist = []
for item in ms.menus.values():
    for v in item:
        print(v.toJSON())
        mylist.append(v.toJSON())
import json
print(json.dumps(mylist))