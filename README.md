<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# Employee Directory with Servlets

[![Java Servlet CI](https://github.com/TwilioDevEd/employee-directory-servlets/actions/workflows/gradle.yml/badge.svg)](https://github.com/TwilioDevEd/employee-directory-servlets/actions/workflows/gradle.yml)

Use Twilio to accept SMS messages and translate them into queries against a
[SQLite](//www.sqlite.org/) database. These example functions show how to access an Employee Directory via SMS. A
mobile phone user can send a text message with a partial string of the person's name and it will return their picture and contact information (Email address and Phone number).

### Prerequisites

1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed in your operative system.
1. A Twilio account with a verified [phone number][twilio-phone-number]. (Get a [free account](//www.twilio.com/try-twilio?utm_campaign=tutorials&utm_medium=readme) here.).  If you are using a Twilio Trial Account, you can learn all about it [here](https://www.twilio.com/help/faq/twilio-basics/how-does-twilios-free-trial-work).

### Local Development

1. First clone this repository and `cd` into it.

   ```
   $ git clone git@github.com:TwilioDevEd/employee-directory-servlets.git
   $ cd employee-directory-servlets
   ```

1. Edit the sample configuration file `.env.example` and edit it to match your configuration.

   You can use the `.env.example` in a unix based operative system with the `source` command to load the variables into your environment:

   ```bash
   $ source .env.example
   ```

   If you are using a different operating system, make sure that all the
   variables from the `.env.example` file are loaded into your environment.

1. Make sure the tests succeed.

   ```bash
   $ ./gradlew check
   ```

1. Start the server.

   ```bash
   $ ./gradlew appRun
   ```

### Expose the Application to the Wider Internet

1. Expose your local web server to the internet using a tool like [ngrok](//ngrok.com/).

   You can click [here](https://www.twilio.com/blog/2015/09/6-awesome-reasons-to-use-ngrok-when-testing-webhooks.html) for more details.
   This step is important because the application won't work as expected if you run it using `localhost`.

   ```bash
   $ ngrok http 8080
   ```

   Once ngrok is running, open up your browser and go to your ngrok URL. It will look something like this:

     `http://<sub-domain>.ngrok.io/`

1. Configure Twilio to call your webhooks.

   You will also need to configure Twilio to call your application when SMSs are received
   on your _Twilio Number_. The **SMS & MMS Request URL** should look something like this:

   ```
   http://<sub-domain>.ngrok.io/directory/search
   ```

   The used method is **HTTP POST**.

   ![Configure SMS](http://howtodocs.s3.amazonaws.com/twilio-number-config-all-med.gif)

That's it!

### Running the app

Each time the applications loads the `IndexServlet#init` function will create a new SQLite database called `employee_db.sqlite`, seeded with data from the `seed-data.json` file, i.e., Data provided by Marvel. &copy; 2016 MARVEL.

1. You can query the employees by sending SMSs to your [Twilio Phone Number][twilio-phone-number].

1. To query employees in your application directly from the web check [http://localhost:8080](http://localhost:8080) or the `http://<sub-domain>.ngrok.io` domain generated by ngrok.

### How To Demo?

1. Text your twilio number the name "Iron".

1. Should get the following response.

   ```
   We found multiple people, reply with:
   1 for Iron Man
   2 for Iron Clad
   Or start over
   ```

1. Reply with 1.

1. Should get the following response.

   ```
   Iron Man
   +14155559368
   +1-202-555-0143
   IronMan@heroes.example.com
   [the image goes here]
   ```

1. If your query matches only one employee, he/she will be returned immediately.

   Eg.: "Spider" will return in the web version:

   ![Query an employee partially](http://howtodocs.s3.amazonaws.com/employee-directory-lookup.png)

[twilio-phone-number]: https://www.twilio.com/console/phone-numbers/incoming

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
