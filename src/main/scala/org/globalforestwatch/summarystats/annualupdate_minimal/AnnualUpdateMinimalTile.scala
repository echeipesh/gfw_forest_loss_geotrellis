package org.globalforestwatch.summarystats.annualupdate_minimal

import geotrellis.raster.{CellGrid, CellType}
import org.globalforestwatch.layers._

/**
  *
  * Tile-like structure to hold tiles from datasets required for our summary.
  * We can not use GeoTrellis MultibandTile because it requires all bands share a CellType.
  */
case class AnnualUpdateMinimalTile(
                                    loss: TreeCoverLoss#ITile,
                                    gain: TreeCoverGain#ITile,
                                    tcd2000: TreeCoverDensityThreshold#ITile,
                                    tcd2010: TreeCoverDensityThreshold#ITile,
                                    biomass: BiomassPerHectar#OptionalDTile,
                                    //                         mangroveBiomass: MangroveBiomass#OptionalDTile,
                                    drivers: TreeCoverLossDrivers#OptionalITile,
                                    globalLandCover: GlobalLandcover#OptionalITile,
                                    primaryForest: PrimaryForest#OptionalITile,
                                    //                         idnPrimaryForest: IndonesiaPrimaryForest#OptionalITile,
                                    //                         erosion: Erosion#OptionalITile,
                                    //                         biodiversitySignificance: BiodiversitySignificance#OptionalDTile,
                                    //                         biodiversityIntactness: BiodiversityIntactness#OptionalDTile,
                                    wdpa: ProtectedAreas#OptionalITile,
                                    aze: Aze#OptionalITile,
                                    plantations: Plantations#OptionalITile,
                                    //                         riverBasins: RiverBasins#OptionalITile,
                                    //                         ecozones: Ecozones#OptionalITile,
                                    //                         urbanWatersheds: UrbanWatersheds#OptionalITile,
                                    mangroves1996: Mangroves1996#OptionalITile,
                                    mangroves2016: Mangroves2016#OptionalITile,
                                    //                         waterStress: WaterStress#OptionalITile,
                                    intactForestLandscapes: IntactForestLandscapes#OptionalITile,
                                    //                         endemicBirdAreas: EndemicBirdAreas#OptionalITile,
                                    tigerLandscapes: TigerLandscapes#OptionalITile,
                                    landmark: Landmark#OptionalITile,
                                    landRights: LandRights#OptionalITile,
                                    keyBiodiversityAreas: KeyBiodiversityAreas#OptionalITile,
                                    mining: Mining#OptionalITile,
                                    //                         rspo: RSPO#OptionalITile,
                                    peatlands: Peatlands#OptionalITile,
                                    oilPalm: OilPalm#OptionalITile,
                                    idnForestMoratorium: IndonesiaForestMoratorium#OptionalITile,
                                    //                         idnLandCover: IndonesiaLandCover#OptionalITile,
                                    //                         mexProtectedAreas: MexicoProtectedAreas#OptionalITile,
                                    //                         mexPaymentForEcosystemServices: MexicoPaymentForEcosystemServices#OptionalITile,
                                    //                         mexForestZoning: MexicoForestZoning#OptionalITile,
                                    //                         perProductionForest: PeruProductionForest#OptionalITile,
                                    //                         perProtectedAreas: PeruProtectedAreas#OptionalITile,
                                    //                         perForestConcessions: PeruForestConcessions#OptionalITile,
                                    //                         braBiomes: BrazilBiomes#OptionalITile,
                                    woodFiber: WoodFiber#OptionalITile,
                                    resourceRights: ResourceRights#OptionalITile,
                                    logging: Logging#OptionalITile,
                                    //                         oilGas: OilGas#OptionalITile
                                    grossEmissionsCo2eNonCo2: GrossEmissionsNonCo2Co2e#OptionalFTile,
                                    grossEmissionsCo2eCo2Only: GrossEmissionsCo2OnlyCo2e#OptionalFTile,
                                    grossCumulAbovegroundRemovalsCo2: GrossCumulAbovegroundRemovalsCo2#OptionalFTile,
                                    grossCumulBelowgroundRemovalsCo2: GrossCumulBelowgroundRemovalsCo2#OptionalFTile,
                                    netFluxCo2: NetFluxCo2e#OptionalFTile
) extends CellGrid[Int] {
  def cellType: CellType = loss.cellType
  def cols: Int = loss.cols
  def rows: Int = loss.rows
}
