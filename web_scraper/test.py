# Testing Scraper

from main import *
ms = MenuScraper()

ms.parse_menus(year="2019", month="4", day="18")

print(ms.return_menu_json())
