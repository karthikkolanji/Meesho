import com.meesho.jakewarton.data.entity.QRScanResult

/*
* Copyright 2021 Wajahat Karim (https://wajahatkarim.com)
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

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
