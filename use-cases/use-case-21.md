# USE CASE: 21 Produce a Report on Top [N] Most Populated Cities in a Country

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list The top N populated cities in a country where N is provided by the user so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains city and country data.

### Success End Condition

A report of top N most populated cities in a specified country is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on top N most populated cities in a specified country.
2. User enters number of desired entries and name of country.
3. User extracts information on cities.
4. User provides report to requester.

## EXTENSIONS

2. **Number of entries invalid:**
    1. User is informed that their entry was invalid.
3. **Country does not exist:**
    1. User informs requester that country given does not exist in database.