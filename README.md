# Munch Map

## What does Munch Map do?
`
- **rate** and **rank** restaurants that you have visited
- **wish-list** promising restaurants to visit in the future
- **track visits** to a restaurant
- **recommend** a random visited or wish-listed restaurant for dinner based on ratings 
- **sort** visited and wish-listed restaurants based on
  - *name*
  - *locations*
  - *rating*
  - *cuisine*
  - *price*
  - *number of times visited*

## Who will use it?

Munch Map is designed for *food lovers* who:
- Have trouble **deciding** where to eat
- Want to find the **cheapest** place to eat
- Want to keep track of **how good** a restaurant was
- Need to know the **closest** restaurant they've been to
- Forget the name of restaurants that **catch their eye**


## Why Munch Map?

My friends and I are extremely indecisive, and have trouble deciding where to eat;
we often spend more time deciding than actually eating. To further complicate things, 
as cash-strapped university students, price is a very important issue for us. This app is created to make finding
where to eat as simple and convenient as possible. Munch Map by recommending only the highest rated or anticipated restaurants 
based on user preferences, eliminating the arduous process of choosing a place to eat. 

## User Stories

- As a user, I want to be able to add multiple restaurants to my list of visited restaurants
- As a user, I want to be able to add multiple locations and dishes to a restaurant
- As a user, I want to be able to select a restaurant and modify its rating, locations, price, review, and number of times eaten
- As a user, I want to be able to sort my visited restaurants by rating, locations, price, or visited
- As a user, I want to be able to randomly view a restaurant above a specific rating or price
- As a user, I want to be able to automatically save my Restaurants to file after pressing q to quit
- as a user, I want to be able to load my Restaurants from my file

## Phase 4: Task 2

Loaded Dennis from file: ./data/RestaurantList.json <br />
Restaurant Samsoonie added on Fri Nov 19 14:28:45 PST 2021  <br />
Restaurant McDonald's added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant So Hyang added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant Nando's added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant Gmen added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant Kokoro added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant A&W added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant Popeye's added on Fri Nov 19 14:28:45 PST 2021 <br />
Restaurant Tim Hortons added on Fri Nov 19 14:28:45 PST 2021 <br />

Process finished with exit code 0

## Phase 4: Task 3

Looking back, if I had known about certain design concepts learned in class, some refactoring I would want to perform include:
- Refactor the relationships between Mainframe, RestaurantList, and Restaurant to remove an association and reduce coupling
- 