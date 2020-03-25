import chalk from 'chalk';
import chokidar from 'chokidar';
import build from './webpack/builder';

const target = process.env.NODE_ENV ?? 'development';

const watchFolders = (folderList: string[]) => folderList.forEach(f => {
  const watcher = chokidar.watch(f);
  watcher.on('ready', () => {
    watcher.on('all', () => {
      console.log(chalk.yellow('Clearing', f, 'module cache from server...'));
      const regex = new RegExp(`[/\\]${f}[/\\]`);
      Object.keys(require.cache).forEach(
        id => (regex.test(id)) && delete require.cache[id]);
    });
  });
});

console.log(chalk.green('Starting app in', target, 'mode...'));
watchFolders(['server', 'webpack']);

build(target);
