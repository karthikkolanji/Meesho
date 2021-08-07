import com.meesho.jakewarton.data.entity.QRScanResult

class MockTestUtil {
    companion object {

        fun qrScanStartRawData():String{
            return "\"{\\\"location_id\\\":\\\"ButterKnifeLib-1234\\\",\\\"location_details\\\":\\\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\\\",\\\"price_per_min\\\":5.50}\""
        }

        fun qrScanEndRawData():String{
            return "\"{\\\"location_id\\\":\\\"ButterKnifeLib-12345\\\",\\\"location_details\\\":\\\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\\\",\\\"price_per_min\\\":5.50}\""
        }

        fun expectedSession(): QRScanResult {
            return QRScanResult(
                location_id = "ButterKnifeLib-1234",
                location_details = "ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore",
                price_per_min = 5.50F,
            )
        }

        fun activeSession(): QRScanResult {
            return QRScanResult(
                location_id = "ButterKnifeLib-1234",
                location_details = "ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore",
                price_per_min = 5.50F,
            )
        }

        fun endSessionQrScan(): QRScanResult {
            return QRScanResult(
                location_id = "ButterKnifeLib-12345",
                location_details = "ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore",
                price_per_min = 5.50F,
            )
        }

        fun endSessionSameQrScan(): QRScanResult {
            return QRScanResult(
                location_id = "ButterKnifeLib-1234",
                location_details = "ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore",
                price_per_min = 5.50F,
            )
        }

    }
}
