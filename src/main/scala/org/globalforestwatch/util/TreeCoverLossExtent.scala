package org.globalforestwatch.util

import geotrellis.vector.MultiPolygon
import geotrellis.vector._

object TreeCoverLossExtent {

  val json: String =
    """{
      |	"type": "MultiPolygon",
      |	"coordinates": [
      |		[
      |			[
      |				[160, -50],
      |				[160, -40],
      |				[170, -40],
      |				[170, -30],
      |				[180, -30],
      |				[180, -40],
      |				[180, -50],
      |				[170, -50],
      |				[160, -50]
      |			]
      |		],
      |		[
      |			[
      |				[-160, 10],
      |				[-160, 20],
      |				[-170, 20],
      |				[-170, 30],
      |				[-160, 30],
      |				[-150, 30],
      |				[-150, 20],
      |				[-150, 10],
      |				[-160, 10]
      |			]
      |		],
      |		[
      |			[
      |				[140, -50],
      |				[140, -40],
      |				[130, -40],
      |				[120, -40],
      |				[110, -40],
      |				[110, -30],
      |				[110, -20],
      |				[110, -10],
      |				[100, -10],
      |				[90, -10],
      |				[90, 0],
      |				[80, 0],
      |				[70, 0],
      |				[70, 10],
      |				[70, 20],
      |				[60, 20],
      |				[60, 10],
      |				[60, 0],
      |				[50, 0],
      |				[50, -10],
      |				[60, -10],
      |				[60, -20],
      |				[60, -30],
      |				[50, -30],
      |				[40, -30],
      |				[40, -40],
      |				[30, -40],
      |				[20, -40],
      |				[10, -40],
      |				[10, -30],
      |				[10, -20],
      |				[10, -10],
      |				[0, -10],
      |				[0, 0],
      |				[-10, 0],
      |				[-20, 0],
      |				[-20, 10],
      |				[-30, 10],
      |				[-30, 20],
      |				[-20, 20],
      |				[-20, 30],
      |				[-20, 40],
      |				[-10, 40],
      |				[-10, 50],
      |				[-20, 50],
      |				[-20, 60],
      |				[-30, 60],
      |				[-30, 70],
      |				[-20, 70],
      |				[-10, 70],
      |				[0, 70],
      |				[10, 70],
      |				[10, 80],
      |				[20, 80],
      |				[30, 80],
      |				[40, 80],
      |				[40, 70],
      |				[50, 70],
      |				[50, 80],
      |				[60, 80],
      |				[70, 80],
      |				[80, 80],
      |				[90, 80],
      |				[100, 80],
      |				[110, 80],
      |				[120, 80],
      |				[130, 80],
      |				[140, 80],
      |				[150, 80],
      |				[160, 80],
      |				[170, 80],
      |				[180, 80],
      |				[180, 70],
      |				[180, 60],
      |				[180, 50],
      |				[170, 50],
      |				[160, 50],
      |				[160, 40],
      |				[150, 40],
      |				[150, 30],
      |				[140, 30],
      |				[140, 20],
      |				[130, 20],
      |				[130, 10],
      |				[140, 10],
      |				[140, 0],
      |				[150, 0],
      |				[160, 0],
      |				[170, 0],
      |				[170, -10],
      |				[180, -10],
      |				[180, -20],
      |				[170, -20],
      |				[170, -30],
      |				[160, -30],
      |				[160, -40],
      |				[150, -40],
      |				[150, -50],
      |				[140, -50]
      |			]
      |		],
      |		[
      |			[
      |				[-80, -60],
      |				[-80, -50],
      |				[-80, -40],
      |				[-80, -30],
      |				[-80, -20],
      |				[-80, -10],
      |				[-90, -10],
      |				[-100, -10],
      |				[-100, 0],
      |				[-100, 10],
      |				[-110, 10],
      |				[-110, 20],
      |				[-120, 20],
      |				[-120, 30],
      |				[-130, 30],
      |				[-130, 40],
      |				[-130, 50],
      |				[-140, 50],
      |				[-150, 50],
      |				[-160, 50],
      |				[-170, 50],
      |				[-180, 50],
      |				[-180, 60],
      |				[-180, 70],
      |				[-170, 70],
      |				[-170, 80],
      |				[-160, 80],
      |				[-150, 80],
      |				[-140, 80],
      |				[-130, 80],
      |				[-120, 80],
      |				[-110, 80],
      |				[-100, 80],
      |				[-90, 80],
      |				[-80, 80],
      |				[-70, 80],
      |				[-60, 80],
      |				[-60, 70],
      |				[-60, 60],
      |				[-50, 60],
      |				[-50, 50],
      |				[-50, 40],
      |				[-60, 40],
      |				[-60, 30],
      |				[-70, 30],
      |				[-70, 20],
      |				[-60, 20],
      |				[-50, 20],
      |				[-50, 10],
      |				[-40, 10],
      |				[-40, 0],
      |				[-30, 0],
      |				[-30, -10],
      |				[-30, -20],
      |				[-40, -20],
      |				[-40, -30],
      |				[-50, -30],
      |				[-50, -40],
      |				[-60, -40],
      |				[-60, -50],
      |				[-50, -50],
      |				[-50, -60],
      |				[-60, -60],
      |				[-70, -60],
      |				[-80, -60]
      |			]
      |		]
      |	]
      |}""".stripMargin


  val geometry: MultiPolygon = json.parseGeoJson[MultiPolygon]
}
