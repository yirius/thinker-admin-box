// @ts-ignore
import type { JSON2SheetOpts, WritingOptions, BookType } from '@/components/xlsx/index';

export interface ExcelData<T> {
  header: string[];
  results: T[];
  meta: { sheetName: string };
}

// export interface ImportProps {
//   beforeUpload: (file: File) => boolean;
// }

export interface JsonToSheet<T> {
  data: T[];
  header?: T;
  filename?: string;
  json2sheetOpts?: JSON2SheetOpts;
  write2excelOpts?: WritingOptions;
}

export interface AoAToSheet<T> {
  data: T[][];
  header?: T[];
  filename?: string;
  write2excelOpts?: WritingOptions;
}

export interface ExportModalResult {
  filename: string;
  bookType: BookType;
}
