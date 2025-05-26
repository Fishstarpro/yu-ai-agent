#!/usr/bin/env node

const fs = require('fs');
const path = require('path');

console.log('🔍 检查构建结果...\n');

const distPath = path.join(__dirname, 'dist');

// 检查dist目录是否存在
if (!fs.existsSync(distPath)) {
    console.log('❌ dist目录不存在，请先运行 npm run build');
    process.exit(1);
}

// 检查关键文件
const requiredFiles = [
    'index.html',
    'assets'
];

let allFilesExist = true;

requiredFiles.forEach(file => {
    const filePath = path.join(distPath, file);
    if (fs.existsSync(filePath)) {
        console.log(`✅ ${file} 存在`);
    } else {
        console.log(`❌ ${file} 不存在`);
        allFilesExist = false;
    }
});

// 检查assets目录内容
const assetsPath = path.join(distPath, 'assets');
if (fs.existsSync(assetsPath)) {
    const assets = fs.readdirSync(assetsPath);
    console.log(`\n📁 assets目录包含 ${assets.length} 个文件:`);
    assets.forEach(asset => {
        const stat = fs.statSync(path.join(assetsPath, asset));
        const size = (stat.size / 1024).toFixed(2);
        console.log(`   - ${asset} (${size} KB)`);
    });
}

// 检查index.html内容
const indexPath = path.join(distPath, 'index.html');
if (fs.existsSync(indexPath)) {
    const indexContent = fs.readFileSync(indexPath, 'utf8');
    if (indexContent.includes('AI智能助手平台') || indexContent.includes('fishstar')) {
        console.log('\n✅ index.html 包含正确的标题和作者信息');
    } else {
        console.log('\n⚠️  index.html 可能有问题');
    }
}

if (allFilesExist) {
    console.log('\n🎉 构建验证通过！');
    console.log('\n📋 部署清单:');
    console.log('1. 将 dist/ 目录内容上传到服务器');
    console.log('2. 配置nginx使用 nginx.conf.example');
    console.log('3. 确保后端服务运行在8112端口');
    console.log('4. 访问 /health.html 检查服务状态');
} else {
    console.log('\n❌ 构建验证失败，请检查构建过程');
    process.exit(1);
} 