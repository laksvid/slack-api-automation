# slack-api-automation
## Description 
The project uses slack API methods to automate some of the basic functionalities of Slack like creating , joining and renaming the channels etc.

## Design 
Uses Java8 and TestNG for the service and test classes. The directory structure holds the src and test sub-folders.

SlackService class : Implementation of the API methods (create, join, rename, list, archive and info of the channels)

ApiService class : Provides access to the API using token and creates a base class for implentation.

PropertiesInirtializer class : Used to read the properties for the external property files (config.properties and token.properties)

SlackApiTest class : Holds the test cases to cover the scenarios of the mentioned API methods

## Flow of the test cases 
1. conversations.create - Create a slack channel.
2. conversations.join - Join the above created slack channel.
3. conversations.rename - Rename the step 1 created slack channel.
4. conversations.list - List the channels existed and checks if the rename of the channel is successful.
5. conversations.archive - Archive the created slack channel.
6. conversations.info - Check if the archived slack channel is archived or not.
7. Negative cases are included to check for the existing channel details.

## Steps to run 
Use -Dprops.file.path={path where the project exists} to run the test cases.

## Note :
Conversations API are used instead of Channels as these methods are officially deprecated.

[Refer this](https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api)