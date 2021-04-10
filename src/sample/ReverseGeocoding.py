import sys
import geopy
from geopy.geocoders import Nominatim
from geopy.extra.rate_limiter import RateLimiter


def my_test(lat, lon):
    locator = Nominatim(user_agent='google')
    coordinates = lat, lon
    location = locator.reverse(coordinates)
    print(location)
    with open('.\\src\\sample\\Resources\\location.txt'
              , 'w') as f:
        f.write(str(location))
    

    # return location
    # specify path or else it will be created where you run your java code

    # with open("C:/Users/HP/Documents/OnlineMandi/src/sample/Resources/location.txt"
    #           , 'w') as target:
    #     target.write(location)

my_test(sys.argv[1], sys.argv[2])
