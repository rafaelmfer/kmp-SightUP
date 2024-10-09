import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.europa.sightup.data.remote.response.ExerciseResponse
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.clock
import sightupkmpapp.composeapp.generated.resources.pone


@Composable
fun CardExercise(modifier: Modifier, exercise: ExerciseResponse) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.pone),
            contentDescription = "Descrição da imagem",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.title,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                color = Color(0xFF1E1E1E)
            )

            Box(
                Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFF1E2022),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(Res.drawable.clock),
                        contentDescription = "Descrição da imagem",
                        modifier = Modifier
                            .width(13.dp)
                            .height(13.dp)
                            .padding(start = 3.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = exercise.duration,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(end = 3.dp),
                        color = Color(0xFF1E2022)
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = exercise.subtitle,
            color = Color(0xFF1E2022),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = exercise.description,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Helps with:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.helps[0],
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF1E2022),
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = exercise.helps[1],
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF1E2022),
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(top = 8.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = exercise.helps[2],
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF1E2022),
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(top = 8.dp, bottom = 8.dp)
            )

        }

    }
    Spacer(modifier = Modifier.height(16.dp))
}