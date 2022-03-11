<a href="https://www.twilio.com">
  <img src="https://static0.twilio.com/marketing/bundles/marketing/img/logos/wordmark-red.svg" alt="Twilio" width="250" />
</a>

# Employee Directory with Servlets

[![Java Servlet CI](https://github.com/TwilioDevEd/employee-directory-servlets/actions/workflows/gradle.yml/badge.svg)](https://github.com/TwilioDevEd/employee-directory-servlets/actions/workflows/gradle.yml)

Use Twilio to accept SMS messages and translate them into queries against a
[SQLite](//www.sqlite.org/) database. These example functions show how to access an Employee Directory via SMS. A
mobile phone user can send a text message with a partial string of the person's name and it will return their picture and contact information (Email address and Phone number).

## Setup

### Prerequisites

1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed in your operative system.
1. A Twilio account with a verified [phone number][twilio-phone-number]. (Get a [free account](//www.twilio.com/try-twilio?utm_campaign=tutorials&utm_medium=readme) here.).  If you are using a Twilio Trial Account, you can learn all about it [here](https://www.twilio.com/docs/usage/tutorials/how-to-use-your-free-trial-account).

### Twilio Account Settings

This application should give you a ready-made starting point for writing your
own appointment reminder application. Before we begin, we need to collect
all the config values we need to run the application:

| Config Value | Description                                                                                                                                                  |
| :---------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Account Sid  | Your primary Twilio account identifier - find this [in the Console](https://www.twilio.com/console).                                                         |
| Auth Token   | Used to authenticate - [just like the above, you'll find this here](https://www.twilio.com/console).                                                         |
| Phone number | A Twilio phone number in [E.164 format](https://en.wikipedia.org/wiki/E.164) - you can [get one here](https://console.twilio.com/us1/develop/phone-numbers/manage/search?frameUrl=%2Fconsole%2Fphone-numbers%2Fsearch%3Fx-target-region%3Dus1&currentFrameUrl=%2Fconsole%2Fphone-numbers%2Fsearch%3FisoCountry%3DUS%26searchTerm%3D%26searchFilter%3Dleft%26searchType%3Dnumber%26x-target-region%3Dus1%26__override_layout__%3Dembed%26bifrost%3Dtrue) |

### Local Development

1. First clone this repository and `cd` into it.

   ```
   git clone git@github.com:TwilioDevEd/employee-directory-servlets.git
   cd employee-directory-servlets
   ```

1.  Set your environment variables

    ```bash
    cp .env.example .env
    ```
   See [Twilio Account Settings](#twilio-account-settings) to locate the necessary environment variables.


1. Make sure the tests succeed.

   ```bash
   ./gradlew check
   ```

1. Start the server.

   ```bash
   ./gradlew appRun
   ```

### Expose the Application to the Wider Internet

1. Expose your local web server to the internet using a tool like [ngrok](//ngrok.com/).

   You can click [here](https://www.twilio.com/blog/2015/09/6-awesome-reasons-to-use-ngrok-when-testing-webhooks.html) for more details.
   This step is important because the application won't work as expected if you run it using `localhost`.

   ```bash
   ngrok http 8080
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

   [Learn how to configure your Twilio phone number for SMS](https://support.twilio.com/hc/en-us/articles/223136047-Configure-a-Twilio-Phone-Number-to-Receive-and-Respond-to-Messages#h_5fd3801f-8241-421f-ad0f-8fb6c25ba68c)

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

1. If your query matches only one employee, they will be returned immediately.

[twilio-phone-number]: https://console.twilio.com/us1/develop/phone-numbers/manage/active?frameUrl=%2Fconsole%2Fphone-numbers%2Fincoming%3Fx-target-region%3Dus1

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
