## Changelog

### Breaking Changes
N/A

### New Features
* `publication("name") {...}` DSL will now try to find an existing publication with the same name before creating a new one
* `npm.publish.dry=true` property can now be passed in without specifying true as `-Pnpm.publish.dry`
(`=false` is still respected)

### Fixes
N/A
