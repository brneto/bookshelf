import chalk from 'chalk';
import open from 'open';
import * as server from './config';
import * as routers from './routers';

const
  isInDev = process.env.NODE_ENV !== 'production',
  port = server.port,
  clientDir = server.path,
  listenerHandler = error =>
    error
      ? console.log(chalk.red(`Server failed to start: [${error}].`))
      : console.log(
          chalk.green(`Server running and listening on port: ${port}.`)
        ) ||
        (isInDev &&
          open(`http://localhost:${port}`).then(
            resolve =>
              console.log(
                `Browser opened with command: '${resolve.spawnargs.join(' ')}'.`
              ),
            reject => console.log(`Failed to open the browser: [${reject}].`)
          )
        ),
  listen = app => app.listen(port, listenerHandler),
  { createSpa } = routers;

export {
  listen,
  createSpa,
  clientDir,
};
