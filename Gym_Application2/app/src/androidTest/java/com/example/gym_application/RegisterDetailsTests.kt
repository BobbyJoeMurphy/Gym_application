import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.example.gym_application.MainActivity
import com.example.gym_application.R
import com.example.gym_application.Register_Details
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisterDetailsTests {

    private lateinit var scenario: ActivityScenario<Register_Details>

    @Before
    fun Settup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)

    }
    @Test
    fun testCreateAUser(){


        val name = "Test1"
        val weight = 255
        val height = 75
        onView(withId(R.id.name)).perform(ViewActions.typeText(name))
        onView(withId(R.id.Weight)).perform(ViewActions.typeText(weight.toString()))
        onView(withId(R.id.height)).perform(ViewActions.typeText(height.toString()))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_register))
    }


}