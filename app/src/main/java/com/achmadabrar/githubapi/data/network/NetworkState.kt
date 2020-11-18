package com.achmadabrar.githubapi.data.network

class NetworkState(val status: Status, val msg: String) {

    enum class Status {
        EMPTY,
        RUNNING,
        SUCCESS,
        FAILED
    }

    companion object {
        val EMPTY =
            NetworkState(
                Status.EMPTY,
                "Empty"
            )
        val LOADED =
            NetworkState(
                Status.SUCCESS,
                "Success"
            )
        val LOADING =
            NetworkState(
                Status.RUNNING,
                "Running"
            )
        val FAILED =
            NetworkState(
                Status.FAILED,
                "Failed"
            )

        fun fialed(errorMessage: String): NetworkState {
            return NetworkState(
                Status.FAILED,
                errorMessage
            )
        }
    }
}