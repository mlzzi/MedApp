package com.leafwise.medapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.leafwise.medapp.presentation.ui.home.HomeScreen
import com.leafwise.medapp.presentation.ui.home.HomeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MedicationSheetTest {


    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    //Dummy data
    private val simulatedUiState = HomeViewModel.HomeUiState.Empty
    private lateinit var textSheetContent: String
    private lateinit var saveEvent: String
    private lateinit var addEvent: String

    @Before
    fun setUp() {
        // Initialize the textSheetContent before each test
        textSheetContent = rule.activity.getString(R.string.dummy_text)
        addEvent = rule.activity.getString(R.string.add)
        saveEvent = rule.activity.getString(R.string.save)
    }

    // Define a common test function that takes the simulatedUiState and onNavigateClick as parameters
    private fun showSheet(homeScreen: @Composable (HomeViewModel.HomeUiState, () -> Unit) -> Unit) {
        // Set the content with the HomeScreen composable and the simulated values
        rule.setContent {
            homeScreen(simulatedUiState) {}
        }

        // Find the button and click to open the modal
        rule.onNodeWithContentDescription(addEvent).assertExists().performClick()

        // Find the modal and assert it's visible
        rule.onNode(hasText(textSheetContent)).assertIsDisplayed()
    }

    @Test
    fun testShowMedSheet() {
        // Call the commonTest function with the HomeScreen Composable function specific to this test
        showSheet { uiState, onNavigateClick ->
            HomeScreen(uiState = uiState, onNavigateClick = onNavigateClick)
        }
    }

    @Test
    fun testSaveMedSheet() {
        // Call the commonTest function with the HomeScreen Composable function specific to this test
        showSheet { uiState, onNavigateClick ->
            HomeScreen(uiState = uiState, onNavigateClick = onNavigateClick)
        }

        // Assert modal is showing
        rule.onNode(hasText(textSheetContent)).assertIsDisplayed()

        // Click save button to close it
        rule.onNodeWithContentDescription(saveEvent).performClick()

        // Find the modal and assert it's not visible
        rule.onNode(hasText(textSheetContent)).assertDoesNotExist()

    }

}