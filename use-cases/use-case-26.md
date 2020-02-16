# USE CASE: 26 Produce a Report on Top [N] Most Populated Capital-Cities

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list The top N populated capital cities in the world where N is provided by the user so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains city data.

### Success End Condition

A report of the top N most populated capital-cities is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on the top N most populated capital-cities
2. User enters number of desired entries.
3. User extracts information on capital-cities.
4. User provides report to requester.

## EXTENSIONS

2. **Number of entries invalid:**
    1. User is informed that their entry was invalid.