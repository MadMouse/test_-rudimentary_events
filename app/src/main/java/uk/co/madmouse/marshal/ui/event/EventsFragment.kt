package uk.co.madmouse.marshal.ui.event

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uk.co.madmouse.marshal.R
import uk.co.madmouse.marshal.core.models.database.Event
import java.util.*

@AndroidEntryPoint
class EventsFragment : Fragment() {

    companion object {
        fun newInstance() = EventsFragment()
    }

    private val viewModel: EventViewModel by viewModels<EventViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    EventApp()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.loadFromWeb();
    }

    @Composable
    fun EventApp() {
        val eventList = viewModel.eventsList.collectAsState()
        Surface {
            Column() {
                debugHeader()
                EventDetails(eventList.value)    
            }
            
        }
    }


    @Composable
    fun debugHeader( modifier: Modifier = Modifier) {
        Row(Modifier.fillMaxWidth(1f)) {
            OutlinedButton(
                onClick = { viewModel.loadFromWeb() },
                Modifier.padding(4.dp)
            ) {
                Text("loadFromWeb")
            }
            OutlinedButton(
                onClick = { viewModel.fetchFromDB() },
                Modifier.padding(4.dp)
            ) {
                Text("db")
            }
        }
    }
    @Composable
    fun EventDetails(eventList: List<Event?>) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(eventList) {
                Card(Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .clickable { },
                    elevation = 5.dp) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row {
                            Text(text = it?.description ?: "N/A",style = MaterialTheme.typography.h6,)
                        }
                        Row {
                            Text(
                                text = getString(R.string.lbl_startDate),
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(4.dp)
                            )
                            val startDate = it?.startDateTime?.let { it1 -> Date(it1 *1000) }
                            Text(text = startDate.toString())
                        }
                        Row {
                            Text(
                                text = getString(R.string.lbl_startDate),
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(4.dp)
                            )
                            val endDate = it?.startDateTime?.let { it1 -> Date(it1 *1000) }
                            Text(text = endDate.toString())
                        }
                        Row {
                            Text(
                                text = getString(R.string.lbl_address),
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(text = it?.address ?: "N/A")
                        }
                    }
                }
            }
        }
    }

}