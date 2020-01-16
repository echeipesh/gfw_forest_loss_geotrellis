package org.globalforestwatch.summarystats.carbonflux

case class CarbonFluxDataGroup(lossYear: Integer,
                               threshold: Integer,
                               isGain: Boolean,
                               isLoss: Boolean,
                               mangroveBiomassExtent: Boolean,
                               drivers: String,
                               ecozones: String,
                               landRights: Boolean,
                               wdpa: String,
                               intactForestLandscapes: String,
                               plantations: String,
                               intactPrimaryForest: Boolean,
                               peatlandsFlux: Boolean,
                               forestAgeCategory: String,
                               jplTropicsAbovegroundBiomassExtent2000: Boolean,
                               fiaRegionsUsExtent: String,
                               braBiomes: String,
                               riverBasins: String,
                               primaryForest: Boolean,
                               isLossLegalAmazon: Boolean,
                               prodesLegalAmazonExtent2000: Boolean
                              )