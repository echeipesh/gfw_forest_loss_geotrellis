package org.globalforestwatch.gladalerts

case class GladAlertsRow(iso: String,
                         adm1: Integer,
                         adm2: Integer,
                         alertDate: String,
                         isConfirmed: Boolean,
                         x: Int,
                         y: Int,
                         z: Int,
                         layers: GladAlertsRowLayers,
                         totalAlerts: Int,
                         totalArea: Double,
                         totalCo2: Double)
