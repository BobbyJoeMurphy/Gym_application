package com.example.gym_application


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.gym_application.Data.*
import com.example.gym_application.databinding.ActivityMainBinding
import com.example.gym_application.userData.Login_Activity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: UserRoomDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val db = Room.databaseBuilder(
            applicationContext,
            UserRoomDatabase::class.java, "Exercises_Table"
        ).allowMainThreadQueries()
            .build()










        lifecycleScope.launch {
            if (db.userDao().readAllData().first().isEmpty())
                exercise(db)
        }



        binding.LoginButton.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding.RegisterButton.setOnClickListener {
            val intent = Intent(this, Register_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun exercise(db: UserRoomDatabase) {


        val chestExercises = arrayListOf(
            TypeOfExercise("Push UP", "Push Ups are one of the best workouts available that you can do in your spare time, You require no Gym specific equipment just DRIVE AND MOTIVATION\n\n1,To do a Push Up correctly Get down on your knees\n2, placing your hands slightly wider than your shoulders\n3, Place your arms our straight\n4, Lowering your body towards the floor\n5, Pause, then back up ", 0,0),
            TypeOfExercise("Close hand push ups", "Identical to a pushup with hands further into the middle of your chest when lifting up,\n" +
                    "\n" +
                    "1,To do a Push Up correctly Get down on your knees\n" +
                    "2, placing your hands inside your shoulder width\n" +
                    "3, Place your arms our straight\n" +
                    "4, Lowering your body towards the floor\n" +
                    "5, Pause, then back up ", 0,0),
            TypeOfExercise("chest3", "This is a chest exercises that is inside fragment 3", 0,0),
            TypeOfExercise("chest4", "This is a chest exercises that is inside fragment 4", 0,0),

        )


        val armsExercises = arrayListOf(TypeOfExercise("arms", "arms exercise", 0,0),
        TypeOfExercise("arms1", "arms exercise", 0,0))

        val legsExercises = arrayListOf(TypeOfExercise("legs", "legs exercise", 0,0),
            TypeOfExercise("legs", "legs exercise", 0,0),
            TypeOfExercise("legs", "legs exercise", 0,0))



        val backExercises = arrayListOf(TypeOfExercise("back", "back exercise", 0,0))


        val exercisesList = arrayListOf<Exercises>(

            Exercises(0, "Chest",R.drawable.common_full_open_on_phone, chestExercises),
            Exercises(0, "Triceps", R.drawable.common_full_open_on_phone,armsExercises),
            Exercises(0, "legs", R.drawable.common_full_open_on_phone,legsExercises),
            Exercises(0, "back", R.drawable.common_full_open_on_phone,backExercises),
            Exercises(0, "ABS", R.drawable.common_full_open_on_phone,backExercises),
            Exercises(0, "Shoulder", R.drawable.common_full_open_on_phone,backExercises),
            Exercises(0, "Biceps", R.drawable.common_full_open_on_phone,backExercises),
            Exercises(0, "Forarm", R.drawable.common_full_open_on_phone,backExercises),
            Exercises(0, "Calf", R.drawable.common_full_open_on_phone,backExercises)







        )
        exercisesList.forEach {

            db.userDao().addUser(it)

        }


    }
}
