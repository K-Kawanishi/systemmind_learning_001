const path = require('path')   // Node.js の `path` モジュール（ファイルパス操作の関数を提供）を読み込み

module.exports = {
    entry: './src/main/typescript/taskList.ts',
    module: {
       rules: [
           {
            test: /\.ts$/,
            use: 'ts-loader',   // package.json にインストール済み
            exclude: /node_modules/,   // `exclude`の理由：パフォーマンスの最適化・・・通常、`node_modules`内のファイルはトランスパイルの必要がないため、`exclude`処理でビルドプロセスから除外
                },
            ],
    },
    resolve: {
                extensions: ['.ts', '.js'],
   },
   output: {
        filename: 'taskList.js',
        path: path.resolve(__dirname, 'src/main/resources/static/js'),
   },
   mode: 'development',
};