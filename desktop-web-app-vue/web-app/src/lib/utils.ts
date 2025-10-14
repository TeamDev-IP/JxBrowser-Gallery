import { type ClassValue, clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'

/**
 * Объединяет классы с учётом условий и устраняет дубликаты,
 * совместимо с Tailwind Merge.
 *
 * Пример:
 * cn('p-2', condition && 'bg-red-500', 'text-sm')
 */
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}