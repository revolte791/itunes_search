package com.hwx.itunessearchbox

import android.view.View
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun View.clicks(): Flow<Unit> = callbackFlow {
    this@clicks.setOnClickListener {
        this.offer(Unit)
    }
    awaitClose { this@clicks.setOnClickListener(null) }
}

fun SearchView.onQueryTextChangeFlow(): Flow<String> = callbackFlow {
    this@onQueryTextChangeFlow.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            offer(query.orEmpty())
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            offer(newText.orEmpty())
            return true
        }
    } )
    awaitClose { this@onQueryTextChangeFlow.setOnQueryTextListener(null) }
}
