package com.excellentwebworld.demomvvmretrofitapi

data class Sponsors(
    var sponsors: List<SponsorDetail>
)
data class SponsorDetail(
    var leg_id: String,
    var name: String,
)
