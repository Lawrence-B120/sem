# USE CASE: 5 Produce a Report on the Population of an Area (World/Continent/Region/Country/District/City)

## CHARACTERISTIC INFORMATION

### Goal in Context

As a user i want to create a population report so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

We know the areas name. Database contains city and country data.

### Success End Condition

A report of the given area's population is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information of area's population
2. User selects area type and enters area name.
3. User extracts information of the specific area's population.
4. User provides report to requester.

## EXTENSIONS

3. **Area does not exist:**
    1. User informs requester that area given does not exist in database.