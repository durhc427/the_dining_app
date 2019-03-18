from flask import escape
from main import *
import sys

def scrape_get(request):

    """HTTP Cloud Function.
    Args:
        request (flask.Request): The request object.
    Returns:
        the response text, or any set of values that can be turned into a
        Response object using 'make_response'
    """
    from bs4 import BeautifulSoup, NavigableString

    import requests

    MenuScraper = Menu_Scraper()
    MenuScraper.parse_menus()
    return MenuScraper.return_all_menus()


def scrape_json_get(request):

    """HTTP Cloud Function.
    Args:
        request (flask.Request): The request object.
    Returns:
        the response text, or any set of values that can be turned into a
        Response object using 'make_response'
    """
    from bs4 import BeautifulSoup, NavigableString

    import requests

    MenuScraper = Menu_Scraper()
    MenuScraper.parse_menus()
    return MenuScraper.return_menu_json()
