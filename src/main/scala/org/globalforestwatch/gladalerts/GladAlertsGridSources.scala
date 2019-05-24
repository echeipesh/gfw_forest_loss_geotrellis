package org.globalforestwatch.gladalerts

import cats.implicits._
import geotrellis.raster.Raster
import geotrellis.vector.Extent
import org.globalforestwatch.grids.{GridId, GridSources}
import org.globalforestwatch.layers._

/**
  * @param gridId top left corner, padded from east ex: "10N_010E"
  */
case class GladAlertsGridSources(gridId: String) extends GridSources {

  lazy val gladAlerts = GladAlerts(gridId)
  lazy val biomassPerHectar = BiomassPerHectar(gridId)
  lazy val climateMask = ClimateMask(gridId)
  lazy val primaryForest = PrimaryForest(gridId)
  lazy val protectedAreas = ProtectedAreas(gridId)
  lazy val aze = Aze(gridId)
  lazy val keyBiodiversityAreas = KeyBiodiversityAreas(gridId)
  lazy val landmark = Landmark(gridId)
  lazy val plantations = Plantations(gridId)
  lazy val mining = Mining(gridId)
  lazy val logging = Logging(gridId)
  lazy val rspo = RSPO(gridId)
  lazy val woodFiber = WoodFiber(gridId)
  lazy val peatlands = Peatlands(gridId)
  lazy val indonesiaForestMoratorium = IndonesiaForestMoratorium(gridId)
  lazy val oilPalm = OilPalm(gridId)
  lazy val indonesiaForestArea = IndonesiaForestArea(gridId)
  lazy val peruForestConcessions = PeruForestConcessions(gridId)
  lazy val oilGas = OilGas(gridId)
  lazy val mangroves2016 = Mangroves2016(gridId)
  lazy val intactForestLandscapes2016 = IntactForestLandscapes2016(gridId)

  def readWindow(window: Extent): Either[Throwable, Raster[GladAlertsTile]] = {

    for {
      // Failure for any of these reads will result in function returning Left[Throwable]
      // These are effectively required fields without which we can't make sense of the analysis
      gladAlertsTile <- Either
        .catchNonFatal(gladAlerts.fetchWindow(window))
        .right

    } yield {
      // Failure for these will be converted to optional result and propagated with TreeLossTile
      val biomassTile = biomassPerHectar.fetchWindow(window)
      val climateMaskTile = climateMask.fetchWindow(window)
      val primaryForestTile = primaryForest.fetchWindow(window)
      val protectedAreasTile = protectedAreas.fetchWindow(window)
      val azeTile = aze.fetchWindow(window)
      val keyBiodiversityAreasTile = keyBiodiversityAreas.fetchWindow(window)
      val landmarkTile = landmark.fetchWindow(window)
      val plantationsTile = plantations.fetchWindow(window)
      val miningTile = mining.fetchWindow(window)
      val loggingTile = logging.fetchWindow(window)
      val rspoTile = rspo.fetchWindow(window)
      val woodFiberTile = woodFiber.fetchWindow(window)
      val peatlandsTile = peatlands.fetchWindow(window)
      val indonesiaForestMoratoriumTile =
        indonesiaForestMoratorium.fetchWindow(window)
      val oilPalmTile = oilPalm.fetchWindow(window)
      val indonesiaForestAreaTile = indonesiaForestArea.fetchWindow(window)
      val peruForestConcessionsTile = peruForestConcessions.fetchWindow(window)
      val oilGasTile = oilGas.fetchWindow(window)
      val mangroves2016Tile = mangroves2016.fetchWindow(window)
      val intactForestLandscapes2016Tile =
        intactForestLandscapes2016.fetchWindow(window)

      val tile = GladAlertsTile(
        gladAlertsTile,
        biomassTile,
        climateMaskTile,
        primaryForestTile,
        protectedAreasTile,
        azeTile,
        keyBiodiversityAreasTile,
        landmarkTile,
        plantationsTile,
        miningTile,
        loggingTile,
        rspoTile,
        woodFiberTile,
        peatlandsTile,
        indonesiaForestMoratoriumTile,
        oilPalmTile,
        indonesiaForestAreaTile,
        peruForestConcessionsTile,
        oilGasTile,
        mangroves2016Tile,
        intactForestLandscapes2016Tile
      )

      Raster(tile, window)
    }
  }

}

object GladAlertsGridSources {

  @transient
  private lazy val cache =
    scala.collection.concurrent.TrieMap.empty[String, GladAlertsGridSources]

  def getCachedSources(grid: String): GladAlertsGridSources = {

    cache.getOrElseUpdate(grid, GladAlertsGridSources(grid))

  }

}
