# Testing API
from main import *
ms = MenuScraper()
ms.parse_menus(year="2019", month="04", day="2")
print(ms.return_menu_json())