from main import *

MenuScraper = Menu_Scraper()
MenuScraper.parse_menus()

#MenuScraper.print_all_menus()

#MenuScraper.dump_json()

#print(MenuScraper.return_all_menus())
print(MenuScraper.return_menu_json())
