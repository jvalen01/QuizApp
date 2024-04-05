# Quiz Application

## Description

This is a simple quiz application that allows users to take a quiz and see their score at the end.
The application has two buttons when starting up - one to start the quiz and one to view the gallery which is a collection of images and their corresponding names
In the gallery a user can also add their own images and name to the gallery, and deleting them by holding down on the image.

When the user starts the quix the user gets an image of a person and three
options with names on who the image is of. The user can then click on the name of the person they think the image is and get a response if they were correct or not. Then a new image will be prompted 
the app also keeps track of the score and displays it throughout the quiz.
Images that are added to the gallery after startup will also be included in the quiz.

## Documentation of test cases and Gradle

When writing the test cases we made three different classes called
GalleryActivityTest, MainActivityTest and QuizActivityTest. 
Each of these classes test the different scenarios mentioned in the assignment description.

When running the command `./gradlew connectedAndroidTest -- info` the tests will run and a lot information will be displayed in the terminal.
The APKs that are used during testing are the apps debugs apk app-debug.apk and the test apk app-debug-androidTest.apk.
These APKs are installed on the device emulator-5554 and the tests are run on this device.

Searching through the output of the command we could not sem to find any reference to adb commands being run.
but gradle uses the command ´adb install´ to install the APKs on the device.
and it likely uses the command ´adb shell am instrument´ to run the tests on the device. Comming from standard Android testing.

Here is snippet of the putput showing that all three test cases passes: 

INFO: Execute com.example.quizapp.GalleryActivityTest.testAddingAndDeletingPersonUpdatesCount: PASSED
apr. 05, 2024 11:05:18 A.M. com.google.testing.platform.RunnerImpl$Companion summarizeAndLogTestResult
INFO: Execute com.example.quizapp.MainActivityTest.testNavigationToQuizActivity: PASSED
apr. 05, 2024 11:05:18 A.M. com.google.testing.platform.RunnerImpl$Companion summarizeAndLogTestResult
INFO: Execute com.example.quizapp.QuizActivityTest.testScoreUpdateAfterAnswer: PASSED

### Test cases

This section will give a detailed description of the test cases that are implemented in the application.
What results are expected and what the test is testing. All the tests are located in the androidTest folder in the app/src folder.

In MainActivityTest class, the testNavigationToQuizActivity method aims to verify the app's navigation from the MainActivity to the QuizActivity. 
The test starts by simulating a user click on the Quiz button within the main activity. Following this, it asserts that some of the key UI elements in the QuizActivity are there:
the image view that displays the person and the text view that shows the score. 
It then checks that the score text view displays the initial score text "Score: 0/0", confirming that the score is correctly initialized when the quiz starts. 
This seamless transition and the correct display of elements indicate that the navigation to quiz works. The implementation of this test, uses Espresso's onView and click actions alongside the matches assertion.

In the QuizActivityTest class, the method testScoreUpdateAfterAnswer confirms the app's score updating logic in QuizActivity.
By using Espresso's UI testing capabilities, the test automates interaction with the quiz interface. 
It begins by launching the QuizActivity and simulating a user interaction to select the correct answer, identified by the tag "correct."The test then verifies that the score is updated accurately to "1/1," 
Next, the test simulates another interaction where a wrong answer, tagged as "wrong1," is selected, and it asserts that the score text view correctly reflects this second interaction with a score of "1/2." 
This process checks the robustness of the scoring system in the quiz, ensuring that it can accurately handle correct and incorrect answers. 
The use of tagged views in the test enables precise targeting of response options in the UI like in this example: wrong and right answers.
Tags are useful metadata that can be attached to views to facilitate testing and interaction with specific elements in the UI and we tought 
it was the best way to identify the correct and incorrect answers in the quiz. 

In the GalleryActivityTest, the testAddingAndDeletingPersonUpdatesCount method tests both the addition and deletion of a person within the gallery,
ensuring the RecyclerView accurately reflects changes to its dataset. 
The test starts by getting the initial count of items displayed in the gallery.
It then simulates adding a new person by stubbing an intent to return a predefined image URI, mimicking the user selecting an image to associate with the new entry.
Following the addition, the test verifies that the item count in the RecyclerView increments by one, 
which verifies the successful insertion of the a new person.

Subsequently, the method progresses to simulate the deletion of the recently added entry.
By employing a long click on the first item in the RecyclerView, the test triggers the app's deletion functionality,
which prompts the user for confirmation via an AlertDialog.
Upon confirming the deletion, the test then asserts that the item count decrements by one,
which verifies the removal of the person from the gallery. 
Using Espresso's intent mocking and UI interactions, the test gives a validation of one of the gallery's core features.