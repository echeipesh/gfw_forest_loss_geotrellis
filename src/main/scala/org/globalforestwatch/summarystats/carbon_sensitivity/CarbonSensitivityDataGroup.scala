
package org.globalforestwatch.summarystats.carbon_sensitivity

case class CarbonSensitivityDataGroup(lossYear: Integer,
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
                                      prodesLegalAmazonExtent2000: Boolean,
                                      isLoss20012015Mekong: Boolean,
                                      mekongTreeCoverLossExtent: Boolean,
                                      tropicLatitudeExtent: Boolean
                                     )