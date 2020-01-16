package org.globalforestwatch.summarystats.carbon_sensitivity

import cats.Semigroup

/** Summary data per class
  *
  * Note: This case class contains mutable values
  *
  */
case class CarbonSensitivityData(var totalTreecoverLoss: Double,
                                 var totalBiomassLoss: Double,
                                 var totalGrossEmissionsCo2eCo2Only: Double,
                                 var totalGrossEmissionsCo2eNoneCo2: Double,
                                 var totalGrossEmissionsCo2e: Double,
                                 var totalAgcEmisYear: Double,
                                 //                          var bgcEmisYear: Double,
                                 //                          var deadwoodCarbonEmisYear: Double,
                                 //                          var litterCarbonEmisYear: Double,
                                 var totalSoilCarbonEmisYear: Double,
                                 //                          var carbonEmisYear: Double,
                                 var totalTreecoverExtent2000: Double,
                                 var totalArea: Double,
                                 var totalBiomass: Double,
                                 //                          var totalGrossAnnualRemovalsCarbon: Double,
                                 var totalGrossCumulRemovalsCarbon: Double,
                                 var totalNetFluxCo2: Double,
                                 var totalAgc2000: Double,
                                 //                          var totalBgc2000: Double,
                                 //                          var totalDeadwoodCarbon2000: Double,
                                 //                          var totalLitterCarbon2000: Double,
                                 var totalSoil2000: Double,
                                 //                          var totalCarbon2000: Double
                                 //                          var totalGrossEmissionsCo2eCo2Only: Double,
                                 //                          var totalGrossEmissionsCo2eNoneCo2: Double,
                                 //                          var totalGrossEmissionsCo2e: Double,
                                 var totalJplTropicsAbovegroundBiomassDensity2000: Double,
                                 var totalTreecoverLossLegalAmazon: Double
                                ) {
  def merge(other: CarbonSensitivityData): CarbonSensitivityData = {
    CarbonSensitivityData(
      totalTreecoverLoss + other.totalTreecoverLoss,
      totalBiomassLoss + other.totalBiomassLoss,
      totalGrossEmissionsCo2eCo2Only + other.totalGrossEmissionsCo2eCo2Only,
      totalGrossEmissionsCo2eNoneCo2 + other.totalGrossEmissionsCo2eNoneCo2,
      totalGrossEmissionsCo2e + other.totalGrossEmissionsCo2e,
      totalAgcEmisYear + other.totalAgcEmisYear,
      //      bgcEmisYear + other.bgcEmisYear,
      //      deadwoodCarbonEmisYear + other.deadwoodCarbonEmisYear,
      //      litterCarbonEmisYear + other.litterCarbonEmisYear,
      totalSoilCarbonEmisYear + other.totalSoilCarbonEmisYear,
      //      carbonEmisYear + other.carbonEmisYear,
      totalTreecoverExtent2000 + other.totalTreecoverExtent2000,
      totalArea + other.totalArea,
      totalBiomass + other.totalBiomass,
      //      totalGrossAnnualRemovalsCarbon + other.totalGrossAnnualRemovalsCarbon,
      totalGrossCumulRemovalsCarbon + other.totalGrossCumulRemovalsCarbon,
      totalNetFluxCo2 + other.totalNetFluxCo2,
      totalAgc2000 + other.totalAgc2000,
      //      totalBgc2000 + other.totalBgc2000,
      //      totalDeadwoodCarbon2000 + other.totalDeadwoodCarbon2000,
      //      totalLitterCarbon2000 + other.totalLitterCarbon2000,
      totalSoil2000 + other.totalSoil2000,
      //      totalCarbon2000 + other.totalCarbon2000
      //      totalGrossEmissionsCo2eCo2Only + other.totalGrossEmissionsCo2eCo2Only,
      //      totalGrossEmissionsCo2eNoneCo2 + other.totalGrossEmissionsCo2eNoneCo2,
      //      totalGrossEmissionsCo2e + other.totalGrossEmissionsCo2e,
      totalJplTropicsAbovegroundBiomassDensity2000 + other.totalJplTropicsAbovegroundBiomassDensity2000,
      totalTreecoverLossLegalAmazon + other.totalTreecoverLossLegalAmazon
    )
  }
}

object CarbonSensitivityData {
  implicit val lossDataSemigroup: Semigroup[CarbonSensitivityData] =
    new Semigroup[CarbonSensitivityData] {
      def combine(x: CarbonSensitivityData, y: CarbonSensitivityData): CarbonSensitivityData =
        x.merge(y)
    }

}