# TalentBoost2016FinalProject
VMware Talent Boost 2016 Final Task - Henry Ford Assembly Line*<br>

Working commands:<br>
_assemble car|suv 'specifications'_  *<br>
_disassemble 'vin'_  *<br>
_display vin|all_  *<br>
_find engine 'emission standart'_  *  - this command works only for emission standart<br>
_update 'vin' 'emission standart'_  * - only emission standart can be updated<br>
_read 'path'_                        - reads commands from text files in the directory 'path'<br>
_stop_                              - stops the "factory" in orderly manner. Only when it is used all vehicles "made" during runtime will be saved to the permanent storage. <br>

Turbocharger option info:<br>
If a turbocharger is mounted and the input engine power is in:<br>
 1)horse powers, it reduces it with 30% and if there is a match in the
  specifications it is valid.<br>
  example: 233hp = 174kW, 134kW + 30% = 174kW -> engine spec 'engine=p-233hp-T' is
  valid<br>
 2)cubic centimeters and there is a match in the specifications the kW power
  is increased with 30%.<br>
  example: 'engine=p-8l-T' is valid -> engine power in kW = 736 + 30% = 957kW = 1282hp<br>
  
*please see TB-2016-FinalTask.pdf for more info
