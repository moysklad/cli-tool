# Moysklad CLI REMAP 1.1

The command-line tool for moysklad's entities manipulation via remap 1.1. 

## Usage 

1. Start the CLI tool `java -jar cli.jar`
2. Connect to the Moysklad `connect online.moysklad.ru admin@loging password`
3. Select a layer `layer assortment`
4. Execute some command `delete`

## Layers
The layer defines a list of operation over specific entities or a entity.

If you want to see a list of available layers, you must 
prompt this command `layer list`.

Select the layer - `layer layer_name`  
Exit from the layer `exit`

### List of layers 

* Assortment `layer assortment`
  * `archive` archive all assortment 
  * `unarchive` unarchive all assortment
  * `delete` delete all assortment
   