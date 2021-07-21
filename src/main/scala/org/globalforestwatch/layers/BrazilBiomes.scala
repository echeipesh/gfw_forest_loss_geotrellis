package org.globalforestwatch.layers

import org.globalforestwatch.grids.GridTile

case class BrazilBiomes(gridTile: GridTile, kwargs: Map[String, Any]) extends StringLayer with OptionalILayer {

  val datasetName = "bra_biomes"

  val uri: String =
    s"$basePath/$datasetName/$version/raster/epsg-4326/${gridTile.gridSize}/${gridTile.rowCount}/name/gdal-geotiff/${gridTile.tileId}.tif"

  override val externalNoDataValue = "Not applicable"

  def lookup(value: Int): String = value match {
    case 1 => "Caatinga"
    case 2 => "Cerrado"
    case 3 => "Pantanal"
    case 4 => "Pampa"
    case 5 => "Amazônia"
    case 6 => "Mata Atlântica"
    case _ => "Unknown"
  }
}
