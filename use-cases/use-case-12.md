# USE CASE: 12 Produce a Report on Top [N] Most Populated Countries in a Region

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list The top N populated countries in a region where N is provided by the user so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains country data.

### Success End Condition

A report of top N most populated countries in a specified region is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on top N most populated countries in a specified region.
2. User enters number of desired entries and name of region.
3. User extracts information on countries.
4. User provides report to requester.

## EXTENSIONS

2. **Number of entries invalid:**
    1. User is informed that their entry was invalid.
3. **Region does not exist:**
    1. User informs requester that region given does not exist in database.