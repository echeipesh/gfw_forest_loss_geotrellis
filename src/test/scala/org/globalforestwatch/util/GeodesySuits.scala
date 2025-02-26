package org.globalforestwatch.util

import geotrellis.raster.CellSize
import org.globalforestwatch.util.Geodesy.pixelArea
import org.scalatest.funsuite.AnyFunSuite

class GeodesySuits extends AnyFunSuite {

  val cellSize = CellSize(0.00025, 0.00025)

  test("Geodesic Area Lat 0.0") {
    assert(pixelArea(0.0, cellSize) === 769.3170049535535)
  }

  test("Geodesic Area Lat 45.0") {
    assert(pixelArea(45.0, cellSize) === 547.6481292317709)
  }

  test("Geodesic Area Lat 90.0") {
    assert(pixelArea(90.0, cellSize) === 0.0017010416666666667)
  }

  test("Geodesic Area Lat -45.0") {
    assert(pixelArea(-45.0, cellSize) === 547.65048671875)
  }

  test("Geodesic Area Lat -90.0") {
    assert(pixelArea(-90.0, cellSize) === 0.0017010416666666667)
  }
}
