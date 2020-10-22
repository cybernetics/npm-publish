## Changelog

### Breaking Changes
N/A

### New Features
* `publication("name") {...}` DSL will now try to find an existing publication with the same name before creating a new one

### Fixes
* packageAssemble task will not run if kotlinCompile task was skipped (due to missing sources or otherwise)
