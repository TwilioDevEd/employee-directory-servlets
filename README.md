# SMS/MMS Employee Directory on Servlets

[![Build status](//ci.appveyor.com/api/projects/status/github/TwilioDevEd/employee-directory-servlets?svg=true)](//ci.appveyor.com/project/TwilioDevEd/employee-directory-servlets)

Use Twilio to accept SMS messages and translate them into queries against a SQL database. This example functions as an Employee Directory where a mobile phone user can send a text message with a partial string of the person's name and it will return their picture and contact information (Email address and Phone number).

[Read the full tutorial here](//www.twilio.com/docs/tutorials/walkthrough/employee-directory/java/servlets)

### Prerequisites

1. A Twilio account with a provisioned phone number. (Get a [free account](//www.twilio.com/try-twilio?utm_campaign=tutorials&utm_medium=readme) here.)
1. Download IntelliJ IDEA. (Download the [free Community edition](//www.jetbrains.com/idea/download/) here.)

### Download and Run the Application

1. First clone this repository and `cd` into it:
   ```
   git clone git@github.com:TwilioDevEd/employee-directory-servlets.git
   cd employee-directory-servlets
   ```

1. Export the environment variables:

   You can find the AccountSID and the AuthToken at [https://www.twilio.com/user/account/settings](https://www.twilio.com/user/account/settings).

   ```
   export TWILIO_ACCOUNT_SID=your account sid
   export TWILIO_AUTH_TOKEN=your auth token
   ```

   The Twilio Phone Number can be found at [https://www.twilio.com/user/account/phone-numbers/incoming](https://www.twilio.com/user/account/phone-numbers/incoming).

   ```
   export TWILIO_PHONE_NUMBER=your Twilio phone number
   ```

1. Make sure the tests succeed:

   ```
   mvn compile test
   ```

1. Run the application.

   ```
   mvn compile && mvn jetty:run
   ```

1. Each time the applications loads, a servlet listener called `EmployeesSeeder` shall create a new SQLite database in the `App_Data` folder named `Employees.sqlite` seeded with data from the `seed-data.json` file. (Data provided by Marvel. &copy; 2016 MARVEL)

1. Check it out at [http://localhost:8080](http://localhost:8080)