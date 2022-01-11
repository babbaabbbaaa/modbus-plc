import React from 'react';
import { Select,Input,DatePicker,InputNumber } from 'antd';

const { TextArea } = Input;
const { Option } = Select;
const { RangePicker } = DatePicker;

const FormCondition = (condition) => {
	const { controlType,options,type,disabled,mode,disabledDate,defaultValue,changeValue,placeholder,showSearch,filterOption,allowClear,format,showTime } = condition;
	switch (controlType) {
		case 'TextArea':
			return (
				<TextArea
					autoSize={{ minRows: 2, maxRows: 6 }}
					allowClear
					placeholder={placeholder}
					defaultValue={defaultValue}
					onChange={changeValue}
					disabled={disabled}
				/>
			);
		case 'DatePicker':
			return (
				<DatePicker 
					disabled={disabled} 
					format={format}
					disabledDate={disabledDate} 
					placeholder={placeholder}
					onChange={changeValue}
				/>
			);
		case 'RangePicker':
			return (
				<RangePicker 
					disabled={disabled} 
					format={format}
					showTime={showTime}
					placeholder={placeholder}
					disabledDate={disabledDate} 
					onChange={changeValue}
				/>
			);
		case 'Select':
			return (
				<Select
          mode={mode}
					allowClear={allowClear===undefined? true:allowClear}
					defaultValue={defaultValue}
					onChange={changeValue}
					maxTagCount={2}
					showSearch={showSearch}
					optionFilterProp="children"
					getPopupContainer={(triggerNode) => triggerNode.parentElement}
					disabled={disabled}
					placeholder={placeholder}
					filterOption={filterOption}
				>
					{
						options && options.length > 0
						&& options.map(({ value, label,disabled }) => (
							<Option key={`${value}${label}`} value={value} disabled={disabled}>{label}</Option>
						))
					}
				</Select>
			);
		case 'Input':
			return (
				<Input
					allowClear
					type={type||'text'}
					placeholder={placeholder}
					defaultValue={defaultValue}
					onChange={changeValue}
					disabled={disabled}
				/>
			);
			case 'InputNumber':
				return (
					<InputNumber
						disabled={disabled}
					/>
				);
    default : return;
	}
};

export default FormCondition;