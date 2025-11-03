import { InjectionKey } from 'vue'

export const tooltipContextKey: InjectionKey<{ delay: number }> = Symbol('TooltipContext')
