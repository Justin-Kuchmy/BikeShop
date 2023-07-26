import { AbstractControl } from '@angular/forms';

export class Validator
{
    public ValidateEmail(control: AbstractControl) : { invalidEmail: boolean } | null 
    {
     const EMAIL_REGEXP = /^(?![_.-])((?![_.-][_.-])[a-zA-Z\d_.-]){0,63}[a-zA-Z\d]@((?!-)((?!--)[a-zA-Z\d-]){0,63}[a-zA-Z\d]\.){1,2}([a-zA-Z]{2,14}\.)?[a-zA-Z]{2,14}/;
     return !EMAIL_REGEXP.test(control.value) ? { invalidEmail: true } : null;
    } // ValidateEmail

    public ValidatePhone(control: AbstractControl): { invalidPhone: boolean } | null 
    {
    const PHONE_REGEXP = /^[(]{0,1}[0-9]{3}[)\.\- ]{0,1}[0-9]{3}[\.\- ]{0,1}[0-9]{4}$/;
    return !PHONE_REGEXP.test(control.value) ? { invalidPhone: true } : null;
    } // ValidatePhone

    public ValidatePostalCode(control: AbstractControl): { invalidPostalCode: boolean } | null 
    {
    const POSTALCODE_REGEXP = /^[A-Za-z]\d[A-Za-z][ -]?\d[A-Za-z]\d$/;
    return !POSTALCODE_REGEXP.test(control.value) ? { invalidPostalCode: true } : null;
    } // ValidatePostalCode 
    public CurrencyValidator(control: AbstractControl): { invalidCurrency: boolean } | null
    {
        const CURRENCY_REGEXP = /^(?!0,?\d)([0-9]{2}[0-9]{0,}(\.[0-9]{2}))/;
        const result = !CURRENCY_REGEXP.test(control.value) ? {invalidCurrency: true } : null;
        return result;
    }
    public IntegerValidator(control: AbstractControl): { invalidnumber: boolean } | null
    {
        const INTEGER_REGEXP = /^\d+$/;
        const result = !INTEGER_REGEXP.test(control.value) ? {invalidnumber: true } : null;
        return result;
    }
    public vendorValidator(control: AbstractControl): { invalidVendor: boolean } | null
    {
        const result = control.value === 0 ? {invalidVendor: true } : null;
        return result;
    }


} 
