"use strict";
var __spreadArray = (this && this.__spreadArray) || function (to, from) {
    for (var i = 0, il = from.length, j = to.length; i < il; i++, j++)
        to[j] = from[i];
    return to;
};
exports.__esModule = true;
exports.aoaToSheetXlsx = exports.jsonToSheetXlsx = void 0;
// @ts-ignore
var index_1 = require("@/components/xlsx/index");
var utils = index_1["default"].utils, writeFile = index_1["default"].writeFile;
var DEF_FILE_NAME = 'new-excel.xlsx';
function jsonToSheetXlsx(_a) {
    var _b;
    var data = _a.data, header = _a.header, _c = _a.filename, filename = _c === void 0 ? DEF_FILE_NAME : _c, _d = _a.json2sheetOpts, json2sheetOpts = _d === void 0 ? {} : _d, _e = _a.write2excelOpts, write2excelOpts = _e === void 0 ? { bookType: 'xlsx' } : _e;
    var arrData = __spreadArray([], data);
    if (header) {
        arrData.unshift(header);
        json2sheetOpts.skipHeader = true;
    }
    var worksheet = utils.json_to_sheet(arrData, json2sheetOpts);
    /* add worksheet to workbook */
    var workbook = {
        SheetNames: [filename],
        Sheets: (_b = {},
            _b[filename] = worksheet,
            _b)
    };
    /* output format determined by filename */
    writeFile(workbook, filename, write2excelOpts);
    /* at this point, out.xlsb will have been downloaded */
}
exports.jsonToSheetXlsx = jsonToSheetXlsx;
function aoaToSheetXlsx(_a) {
    var _b;
    var data = _a.data, header = _a.header, _c = _a.filename, filename = _c === void 0 ? DEF_FILE_NAME : _c, _d = _a.write2excelOpts, write2excelOpts = _d === void 0 ? { bookType: 'xlsx' } : _d;
    var arrData = __spreadArray([], data);
    if (header) {
        arrData.unshift(header);
    }
    var worksheet = utils.aoa_to_sheet(arrData);
    /* add worksheet to workbook */
    var workbook = {
        SheetNames: [filename],
        Sheets: (_b = {},
            _b[filename] = worksheet,
            _b)
    };
    /* output format determined by filename */
    writeFile(workbook, filename, write2excelOpts);
    /* at this point, out.xlsb will have been downloaded */
}
exports.aoaToSheetXlsx = aoaToSheetXlsx;
