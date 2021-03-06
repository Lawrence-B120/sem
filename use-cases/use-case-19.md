# USE CASE: 19 Produce a Report on Top [N] Most Populated Cities in a Continent

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list The top N populated cities in a continent where N is provided by the user so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains city and continent data.

### Success End Condition

A report of top N most populated cities in a specified continent is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on top N most populated cities in a specified continent.
2. User enters number of desired entries and name of continent.
3. User extracts information on cities.
4. User provides report to requester.

## EXTENSIONS

2. **Number of entries invalid:**
    1. User is informed that their entry was invalid.
3. **Continent does not exist:**
    1. User informs requester that continent given does not exist in database.